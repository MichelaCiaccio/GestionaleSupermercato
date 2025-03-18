import db from '@/lib/db';
import { columns } from './products-column';
import { ProductsDataTable } from './products-data-table';
import { Product } from '@/types/db';

type ProductsTableProps = {
  search: string;
  currentPage: number;
  order: 'asc' | 'desc';
  sort: string;
};

export async function ProductsTable({
  search,
  currentPage,
  order,
  sort,
}: ProductsTableProps) {
  let products: Product[];
  let totalPages = 1;

  try {
    products = await db.products.getAll();
    // For now, pagination size is hard coded to 20.
    totalPages = Math.ceil(products.length / 20);
  } catch {
    products = [];
  }

  return (
    <ProductsDataTable
      search={search}
      currentPage={currentPage}
      totalPages={totalPages}
      order={order}
      sort={sort}
      columns={columns}
      data={products}
    />
  );
}
