/* Utils */
import { useEffect, useState } from "react";
import toast, { Toaster } from 'react-hot-toast';
import Util from './utils'

/* Api */
import API from './api/account';

/* Styles */
import './App.css';

function App() {
  const [accounts, setAccounts] = useState([]);
  const [accountName, setAccountName] = useState("");
  const [transferData, setTransferData] = useState({ fromId: "", toId: "", amount: "" });

  const loadAccounts = async () => {
    await API.getAccounts().then((data) => {
      setAccounts(data);
    });
  };

  const handleCreateAccount = async () => {
    if (!accountName.trim()) return;
    try {
      const account = await API.createAccount(accountName);
      setAccounts(prev => [...prev, account]);
      setAccountName("");
      toast.success("Successful Account creation !")
    } catch (err) {
      toast.error(err.message)
    }
  }

  const handleDeposit = async (id) => {
    try {
      const amount = prompt("Deposit amount:");
      if (!Util.isValidAmount(amount)) {
        toast.error("Invalid amount! Must be a positive amount");
        return;
      }
      
      const account = await API.deposit(id, amount);
      setAccounts(prev =>
        prev.map(a => a.id === id ? account : a)
      );

      setAccountName("");
      toast.success("Successful Deposit !")
    } catch (err) {
      alert(err.message);
    }
  };

  const handleWithdraw = async (id) => {
    try {
      const amount = prompt("Withdraw amount:");
      if (!Util.isValidAmount(amount)) {
        toast.error("Invalid amount! Must be a positive amount");
        return;
      }

      const account = await API.withdraw(id, amount);
      setAccounts(prev =>
        prev.map(a => a.id === id ? account : a)
      );
      
      setAccountName("");
      toast.success("Successful Withdraw !")
    } catch (err) {
      toast.error(err.message)
    }
  };

  const handleTransfer = async () => {
    const { fromId, toId, amount } = transferData;
    if (!fromId || !toId || !amount) return;
    try {
      await API.transfer(Number(fromId), Number(toId), Number(amount));

      // Reload accounts
      const updatedAccounts = await API.getAccounts();
      setAccounts(updatedAccounts);

      // Reset input fields
      setTransferData({ fromId: "", toId: "", amount: "" });
      toast.success("Successful Tranfer !")
    } catch (err) {
      toast.error(err.message)
    }
  };

  useEffect(() => {
    loadAccounts();
  }, []);

  return (
    <div className="p-8 font-sans max-w-4xl mx-auto">
      <Toaster position="top-right" reverseOrder={false} />
      <h1 className="text-3xl font-bold mb-6">Fast & Reckless Bank</h1>

      {/* Create Account */}
      <div className="mb-6 flex gap-2">
        <input
          type="text"
          className="border rounded px-2 py-1 flex-1"
          placeholder="Account name"
          value={accountName}
          onChange={(e) => setAccountName(e.target.value)}
        />
        <button
          className="bg-blue-500 text-white px-4 py-1 rounded"
          onClick={handleCreateAccount}
        >
          Create
        </button>
      </div>

      {/* Accounts List */}
      <h2 className="text-2xl font-semibold mb-4">Accounts</h2>
      <ul className="space-y-4">
        {accounts?.map((acc) => (
          <li key={acc.id} className="p-4 border rounded flex justify-between items-center">
            <div className="flex items-center gap-2">
              <span className="ml-auto bg-indigo-600 text-white text-md px-2 py-0.5 rounded-md">
                ID: {acc.id}
              </span>
              <span className="ml-auto bg-purple-600 text-white text-md px-2 py-0.5 rounded-md">
                Name: {acc.name}
              </span>
              <span className="ml-auto bg-blue-600 text-white text-md px-2 py-0.5 rounded-md">
                Balance: {acc.balance}¥
              </span>
            </div>
            <div className="flex gap-2">
              <button className="bg-green-500 text-white px-2 py-1 rounded" onClick={() => handleDeposit(acc.id)}>Deposit</button>
              <button className="bg-red-500 text-white px-2 py-1 rounded" onClick={() => handleWithdraw(acc.id)}>Withdraw</button>
            </div>
          </li>
        ))}
      </ul>

      {/* Transfer */}
      <h2 className="text-2xl font-semibold mt-6 mb-2">Transfer Money</h2>
      <div className="flex gap-2 mb-6">
        <input
          type="number"
          placeholder="From ID"
          className="border rounded px-2 py-1"
          value={transferData.fromId}
          onChange={(e) => setTransferData({ ...transferData, fromId: e.target.value })}
        />
        <input
          type="number"
          placeholder="To ID"
          className="border rounded px-2 py-1"
          value={transferData.toId}
          onChange={(e) => setTransferData({ ...transferData, toId: e.target.value })}
        />
        <input
          type="number"
          placeholder="Amount"
          className="border rounded px-2 py-1"
          value={transferData.amount}
          onChange={(e) => setTransferData({ ...transferData, amount: e.target.value })}
        />
        <button className="bg-purple-500 text-white px-4 py-1 rounded" onClick={handleTransfer}>Transfer</button>
      </div>
    </div>
  );
}

export default App;
