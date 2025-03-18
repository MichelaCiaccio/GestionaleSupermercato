import { Button } from '@/components/ui/button';
import { DashboardHeader } from '@/components/ui/dashboard/header';
import { PopulateProductsButton } from '@/components/ui/dashboard/products/populate-products';
import { ProductsTable } from '@/components/ui/dashboard/products/products-table';
import { SearchInput } from '@/components/ui/data-table';
import { env } from '@/data/env/server';
import { Plus } from 'lucide-react';
import { Metadata } from 'next';
import Link from 'next/link';

export const metadata: Metadata = {
  title: 'Products',
};

type QueryParams = {
  search?: string;
  page?: string;
  order?: 'asc' | 'desc';
  sort?: string;
};

export default async function ProductsPage(props: {
  searchParams?: Promise<QueryParams>;
}) {
  const searchParams = await props.searchParams;
  const search = searchParams?.search ?? '';
  const order = searchParams?.order ?? 'asc';
  const sort = searchParams?.sort ?? 'name';
  const currentPage = Number(searchParams?.page) || 1;

  return (
    <>
      <DashboardHeader breadcrumbs={[{ label: 'Products' }]} />

      <main className="grid gap-6 p-6">
        <div className="w-full min-w-0">
          <div className="mb-4 flex items-center gap-4">
            <SearchInput className="flex-1" placeholder="Find products..." />

            <Button
              type="button"
              variant="default"
              className="cursor-pointer"
              asChild
            >
              <Link href="/dashboard/products/add">
                <Plus />
                New Product
              </Link>
            </Button>
          </div>

          <ProductsTable
            search={search}
            currentPage={currentPage}
            order={order}
            sort={sort}
          />

          {env.NODE_ENV === 'development' && (
            <PopulateProductsButton className="hidden" />
          )}
        </div>
      </main>
    </>
  );
}
