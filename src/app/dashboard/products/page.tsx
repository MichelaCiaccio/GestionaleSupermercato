import { DashboardHeader } from '@/components/ui/dashboard/header';
import { ProductsTable } from '@/components/ui/dashboard/products/products-table';
import { Metadata } from 'next';

export const metadata: Metadata = {
  title: 'Products',
};

export default function ProductsPage() {
  return (
    <>
      <DashboardHeader breadcrumbs={[{ label: 'Products' }]} />

      <main className="grid gap-6 p-6">
        <ProductsTable />
      </main>
    </>
  );
}
