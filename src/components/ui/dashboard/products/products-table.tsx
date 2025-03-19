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
    const data = await db.products.get();
    products = data.content;

    // Since query functionalities are still limited in the backend
    // then manually get all products:
    const promises: ReturnType<typeof db.products.get>[] = [];
    for (let i = 1; i < data.totalPages; i++)
      promises.push(db.products.get({ page: i }));
    (await Promise.all(promises)).forEach(({ content }) =>
      products.push(...content)
    );

    totalPages = data.totalPages;
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
