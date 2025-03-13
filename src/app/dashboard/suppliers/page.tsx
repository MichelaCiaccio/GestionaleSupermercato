import { DashboardHeader } from '@/components/ui/dashboard/header';
import { SuppliersTable } from '@/components/ui/dashboard/suppliers/suppliers-table';
import { Metadata } from 'next';

export const metadata: Metadata = {
  title: 'Suppliers',
};

export default function SuppliersPage() {
  return (
    <>
      <DashboardHeader title="Suppliers" />

      <main className="grid gap-6 p-6">
        <SuppliersTable />
      </main>
    </>
  );
}
