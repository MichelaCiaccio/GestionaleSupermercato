import { Card, CardContent, CardHeader, CardTitle } from '@/components/ui/card';
import { SalesDataTable } from './sales-data-table';
import { columns } from './sales-columns';

export function SalesTable() {
  return (
    <Card className="min-w-0">
      <CardHeader>
        <CardTitle className="text-base">Recent Sales</CardTitle>
      </CardHeader>
      <CardContent>
        <SalesDataTable columns={columns} data={[]} />
      </CardContent>
    </Card>
  );
}
