import { useEffect, useState } from 'react';
import { useRouter } from 'next/router';
import {
  Box,
  Grid,
  Paper,
  Typography,
  Card,
  CardContent,
} from '@mui/material';
import {
  BarChart,
  Bar,
  XAxis,
  YAxis,
  CartesianGrid,
  Tooltip,
  Legend,
  ResponsiveContainer,
} from 'recharts';
import Layout from '@/components/Layout';
import { dashboardApi } from '@/lib/api';

interface DashboardData {
  stats: {
    totalUsers: number;
    totalPoints: number;
    totalConvertedAmount: number;
  };
  recentTransactions: Array<{
    id: number;
    partnerName: string;
    userName: string;
    amount: number;
    points: number;
    status: string;
    timestamp: string;
  }>;
  weeklyConversions: Array<{
    week: string;
    totalAmount: number;
    totalPoints: number;
    transactionCount: number;
  }>;
}

export default function Dashboard() {
  const [data, setData] = useState<DashboardData | null>(null);
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState('');
  const router = useRouter();

  useEffect(() => {
    const fetchData = async () => {
      try {
        const response = await dashboardApi.getDashboardData();
        setData(response.data);
      } catch (err: any) {
        setError(err.response?.data?.message || 'Failed to load dashboard data');
      } finally {
        setLoading(false);
      }
    };

    fetchData();
  }, []);

  if (loading) {
    return (
      <Layout>
        <Typography>Loading...</Typography>
      </Layout>
    );
  }

  if (error) {
    return (
      <Layout>
        <Typography color="error">{error}</Typography>
      </Layout>
    );
  }

  return (
    <Layout>
      <Box sx={{ flexGrow: 1 }}>
        <Grid container spacing={3}>
          {/* Statistics Cards */}
          <Grid item xs={12} md={4}>
            <Card>
              <CardContent>
                <Typography color="textSecondary" gutterBottom>
                  Total Users
                </Typography>
                <Typography variant="h4">
                  {data?.stats.totalUsers.toLocaleString()}
                </Typography>
              </CardContent>
            </Card>
          </Grid>
          <Grid item xs={12} md={4}>
            <Card>
              <CardContent>
                <Typography color="textSecondary" gutterBottom>
                  Total Points
                </Typography>
                <Typography variant="h4">
                  {data?.stats.totalPoints.toLocaleString()}
                </Typography>
              </CardContent>
            </Card>
          </Grid>
          <Grid item xs={12} md={4}>
            <Card>
              <CardContent>
                <Typography color="textSecondary" gutterBottom>
                  Total Converted Amount
                </Typography>
                <Typography variant="h4">
                  ${data?.stats.totalConvertedAmount.toLocaleString()}
                </Typography>
              </CardContent>
            </Card>
          </Grid>

          {/* Weekly Conversions Chart */}
          <Grid item xs={12}>
            <Paper sx={{ p: 2 }}>
              <Typography variant="h6" gutterBottom>
                Weekly Conversions
              </Typography>
              <Box sx={{ height: 300 }}>
                <ResponsiveContainer width="100%" height="100%">
                  <BarChart data={data?.weeklyConversions}>
                    <CartesianGrid strokeDasharray="3 3" />
                    <XAxis dataKey="week" />
                    <YAxis yAxisId="left" orientation="left" stroke="#8884d8" />
                    <YAxis yAxisId="right" orientation="right" stroke="#82ca9d" />
                    <Tooltip />
                    <Legend />
                    <Bar
                      yAxisId="left"
                      dataKey="totalAmount"
                      name="Total Amount"
                      fill="#8884d8"
                    />
                    <Bar
                      yAxisId="right"
                      dataKey="totalPoints"
                      name="Total Points"
                      fill="#82ca9d"
                    />
                  </BarChart>
                </ResponsiveContainer>
              </Box>
            </Paper>
          </Grid>

          {/* Recent Transactions */}
          <Grid item xs={12}>
            <Paper sx={{ p: 2 }}>
              <Typography variant="h6" gutterBottom>
                Recent Transactions
              </Typography>
              <Grid container spacing={2}>
                {data?.recentTransactions.map((transaction) => (
                  <Grid item xs={12} key={transaction.id}>
                    <Paper sx={{ p: 2 }}>
                      <Grid container spacing={2}>
                        <Grid item xs={12} sm={3}>
                          <Typography variant="subtitle2">Partner</Typography>
                          <Typography>{transaction.partnerName}</Typography>
                        </Grid>
                        <Grid item xs={12} sm={3}>
                          <Typography variant="subtitle2">User</Typography>
                          <Typography>{transaction.userName}</Typography>
                        </Grid>
                        <Grid item xs={12} sm={2}>
                          <Typography variant="subtitle2">Amount</Typography>
                          <Typography>${transaction.amount}</Typography>
                        </Grid>
                        <Grid item xs={12} sm={2}>
                          <Typography variant="subtitle2">Points</Typography>
                          <Typography>{transaction.points}</Typography>
                        </Grid>
                        <Grid item xs={12} sm={2}>
                          <Typography variant="subtitle2">Status</Typography>
                          <Typography>{transaction.status}</Typography>
                        </Grid>
                      </Grid>
                    </Paper>
                  </Grid>
                ))}
              </Grid>
            </Paper>
          </Grid>
        </Grid>
      </Box>
    </Layout>
  );
} 