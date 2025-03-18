import db from '@/lib/db';
import { columns } from './products-column';
import { ProductsDataTable } from './products-data-table';
import { Product } from '@/types/db';
import { DataTableFooter } from '../../data-table';

type ProductsTableProps = {
  search: string;
  currentPage: number;
};

export async function ProductsTable({
  search,
  currentPage,
}: ProductsTableProps) {
  let products: Product[];

  try {
    products = await db.products.getAll();
  } catch {
    products = [];
  }

  return (
    <>
      <ProductsDataTable search={search} columns={columns} data={products} />

      {/* <DataTableFooter /> */}
    </>
  );
}
