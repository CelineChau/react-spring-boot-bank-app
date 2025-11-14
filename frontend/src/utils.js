const utils = {
    isValidAmount: (value) => {
        const num = Number(value);
        return !isNaN(num) && num > 0;
    },

    isValidId: (value) => {
        const num = Number(value);
        return !isNaN(num) && num > 0;
    }
}

export default utils;