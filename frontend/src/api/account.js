import { request } from "./request";

const Account = {
    getAccounts: () => request("/accounts"),

    createAccount: (name) =>
        request("/accounts", {
            method: "POST",
            body: JSON.stringify({ name })
        }),

    deposit: (id, amount) =>
        request(`/accounts/${id}/deposit`, {
            method: "POST",
            body: JSON.stringify({ amount })
        }),

    withdraw: (id, amount) =>
        request(`/accounts/${id}/withdraw`, {
            method: "POST",
            body: JSON.stringify({ amount })
        }),

    transfer: (fromId, toId, amount) =>
        request(`/accounts/transfer`, {
            method: "POST",
            body: JSON.stringify({ fromId, toId, amount })
        }),
};

export default Account;