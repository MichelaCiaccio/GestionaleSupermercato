import { Card, CardContent, CardHeader, CardTitle } from '@/components/ui/card';
import { currencyFormatter } from '@/lib/utils';
import { Banknote, Factory, LucideIcon, Package, Users } from 'lucide-react';
import {
  products,
  sales,
} from '@/components/ui/dashboard/overview/sample-data';

export function CardWrapper() {
  return (
    <>
      <OverviewCard
        title="Total Sales"
        icon={Banknote}
        value={sales.reduce((total, { amount }) => total + amount, 0)}
        isCurrency
      />
      <OverviewCard
        title="Total Products"
        icon={Package}
        value={products.reduce((total, { quantity }) => total + quantity, 0)}
      />
      <OverviewCard title="Total Suppliers" icon={Factory} value={20} />
      <OverviewCard title="Total Users" icon={Users} value={1} />
    </>
  );
}

function OverviewCard(props: {
  title: string;
  value: number;
  isCurrency?: boolean;
  icon: LucideIcon;
}) {
  return (
    <Card className="gap-1.5">
      <CardHeader>
        <CardTitle className="flex items-center justify-between gap-1.5">
          {props.title}
          <props.icon className="text-muted-foreground h-5 w-5" />
        </CardTitle>
      </CardHeader>
      <CardContent>
        <div className="text-2xl font-bold">
          {props.isCurrency
            ? currencyFormatter.format(props.value)
            : props.value}
        </div>
      </CardContent>
    </Card>
  );
}
