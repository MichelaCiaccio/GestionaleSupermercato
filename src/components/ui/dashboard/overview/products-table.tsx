import { Card, CardContent, CardHeader, CardTitle } from '@/components/ui/card';
import { ProductsDataTable } from './products-data-table';
import { columns } from './products-columns';

export function ProductsTable() {
  return (
    <Card className="min-w-0">
      <CardHeader>
        <CardTitle className="text-base">Low Stock Products</CardTitle>
      </CardHeader>
      <CardContent>
        <ProductsDataTable columns={columns} data={[]} />
      </CardContent>
    </Card>
  );
}
