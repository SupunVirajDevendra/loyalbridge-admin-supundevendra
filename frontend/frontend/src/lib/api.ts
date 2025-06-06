import axios from 'axios';

const api = axios.create({
  baseURL: '/api', // This will be proxied to http://localhost:8080/api
  headers: {
    'Content-Type': 'application/json',
  },
});

// Add request interceptor for authentication
api.interceptors.request.use((config) => {
  const token = localStorage.getItem('token');
  if (token) {
    config.headers.Authorization = `Bearer ${token}`;
  }
  return config;
});

// Add response interceptor for error handling
api.interceptors.response.use(
  (response) => response,
  (error) => {
    if (error.response?.status === 401) {
      localStorage.removeItem('token');
      window.location.href = '/login';
    }
    return Promise.reject(error);
  }
);

export const authApi = {
  login: (email: string, password: string, otp?: string) =>
    api.post('/auth/login', { email, password, otp }),
  register: (data: any) => api.post('/auth/register', data),
};

export const userApi = {
  getUsers: (params: any) => api.get('/users/search', { params }),
  getUser: (id: string) => api.get(`/users/${id}`),
  updateUser: (id: string, data: any) => api.put(`/users/${id}`, data),
  updateUserStatus: (id: string, status: string) => 
    api.put(`/users/${id}/status`, null, { params: { status } }),
  updateUserRiskLevel: (id: string, riskLevel: string) =>
    api.put(`/users/${id}/risk-level`, null, { params: { riskLevel } }),
  toggleUserFreeze: (id: string) => api.put(`/users/${id}/freeze`),
  updateUserVerification: (id: string, verified: boolean) =>
    api.put(`/users/${id}/verify`, null, { params: { verified } }),
  exportUsers: (params: any) =>
    api.get('/users/export', { params, responseType: 'blob' }),
};

export const partnerApi = {
  getPartners: () => api.get('/partners'),
  getPartner: (id: string) => api.get(`/partners/${id}`),
  createPartner: (data: any) => api.post('/partners', data),
  updatePartner: (id: string, data: any) => api.put(`/partners/${id}`, data),
  deletePartner: (id: string) => api.delete(`/partners/${id}`),
};

export const transactionApi = {
  getTransactions: (params: any) => api.get('/transactions', { params }),
  getConversionLogs: (params: any) => api.get('/conversion-logs', { params }),
  exportConversionLogs: (params: any) =>
    api.get('/conversion-logs/export', { params, responseType: 'blob' }),
};

export const dashboardApi = {
  getDashboardData: () => api.get('/dashboard'),
};

export default api; 