import { Banknote, Factory, LucideIcon, Package, Users } from 'lucide-react';
import { Card, CardContent, CardHeader, CardTitle } from '../../card';
import { Skeleton } from '../../skeleton';

export function CardWrapperSkeleton() {
  return (
    <>
      <OverviewCardSkeleton title="Total Sales" icon={Banknote} isCurrency />
      <OverviewCardSkeleton title="Total Products" icon={Package} />
      <OverviewCardSkeleton title="Total Suppliers" icon={Factory} />
      <OverviewCardSkeleton title="Total Users" icon={Users} />
    </>
  );
}

export function OverviewCardSkeleton(props: {
  title: string;
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
        <Skeleton className="mt-1 h-7 w-full" />
      </CardContent>
    </Card>
  );
}
