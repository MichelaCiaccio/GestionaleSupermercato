import { CardWrapper } from '@/components/ui/dashboard/overview/card-wrapper';
import { SalesTable } from '@/components/ui/dashboard/overview/sales-table';
import { Metadata } from 'next';

export const metadata: Metadata = {
  title: 'Overview',
};

export default function DashboardPage() {
  return (
    <main className="flex flex-col gap-6 p-6">
      <div className="grid gap-6 sm:grid-cols-2 lg:grid-cols-4">
        <CardWrapper />
      </div>

      <div className="grid gap-6 sm:grid-cols-1 lg:grid-cols-2">
        <SalesTable />
      </div>
    </main>
  );
}
