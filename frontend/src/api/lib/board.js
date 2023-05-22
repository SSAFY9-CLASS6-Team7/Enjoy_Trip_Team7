import { apiInstance } from "./index.js";

const api = apiInstance();

async function getBoardList(user, success, fail) {
  await api.get(`/board/`, JSON.stringify(user)).then(success).catch(fail);
}

export { getBoardList };
