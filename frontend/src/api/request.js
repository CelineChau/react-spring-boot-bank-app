import toast, { Toaster } from 'react-hot-toast';

const API_URL = "http://localhost:8081";

export async function request(path, options = {}) {
    try {
        const res = await fetch(API_URL + path, {
            headers: {
                "Content-Type": "application/json",
                ...(options.headers || {})
            },
            ...options
        });

        if (!res.ok) {
            const message = await res.text();
            throw new Error(message || "Request failed");
        }

        return await res.json();

    } catch (err) {
        console.error("API error:", err);
        throw err;
    }
}