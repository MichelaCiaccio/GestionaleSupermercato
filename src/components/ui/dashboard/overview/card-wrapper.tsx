import { Card, CardContent, CardHeader, CardTitle } from '@/components/ui/card';
import { currencyFormatter } from '@/lib/utils';
import { Banknote, Factory, LucideIcon, Package, Users } from 'lucide-react';

export function CardWrapper() {
  return (
    <>
      <OverviewCard title="Total Sales" icon={Banknote} value={123.5} />
      <OverviewCard title="Total Products" icon={Package} value={123.5} />
      <OverviewCard title="Total Suppliers" icon={Factory} value={123.5} />
      <OverviewCard title="Total Users" icon={Users} value={123.5} />
    </>
  );
}

function OverviewCard(props: {
  title: string;
  value: number;
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
          {currencyFormatter.format(props.value)}
        </div>
      </CardContent>
    </Card>
  );
}
