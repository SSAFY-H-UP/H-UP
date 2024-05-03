import { api } from "../instance/api";

export const onLoadData = async () => {
    return await api({
        url: "/api/issue",
        method: "GET",
        data: { param: {} },
    });
};

export const updateIssue = async (data) => {
    return await api({
        url: "/api/issue",
        method: "POST",
        data: { param: data },
    });
};