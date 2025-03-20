import { DashboardHeader } from '@/components/ui/dashboard/header';
import { CardWrapper } from '@/components/ui/dashboard/overview/card-wrapper';
import { ProductsTable } from '@/components/ui/dashboard/overview/products-table';
import { SalesTable } from '@/components/ui/dashboard/overview/sales-table';
import { CardWrapperSkeleton } from '@/components/ui/dashboard/overview/skeleton';
import { Metadata } from 'next';
import { Suspense } from 'react';

export const metadata: Metadata = {
  title: 'Overview',
};

export default function OverviewPage() {
  return (
    <>
      <DashboardHeader breadcrumbs={[{ label: 'Overview' }]} />

      <main className="flex flex-col gap-6 p-6">
        <div className="grid gap-6 sm:grid-cols-2 lg:grid-cols-4">
          <Suspense fallback={<CardWrapperSkeleton />}>
            <CardWrapper />
          </Suspense>
        </div>
        <div className="grid items-start gap-6 sm:grid-cols-1 lg:grid-cols-2">
          <SalesTable />
          <ProductsTable />
        </div>
      </main>
    </>
  );
}
