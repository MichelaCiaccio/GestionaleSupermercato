import { DashboardHeader } from '@/components/ui/dashboard/header';
import { SuppliersTable } from '@/components/ui/suppliers/suppliers-table';
import { Metadata } from 'next';

export const metadata: Metadata = {
  title: 'Suppliers',
};

export default function ProductsPage() {
  return (
    <>
      <DashboardHeader title="Suppliers" />

      <main className="flex flex-col gap-6 p-6">
        <SuppliersTable />
      </main>
    </>
  );
}
