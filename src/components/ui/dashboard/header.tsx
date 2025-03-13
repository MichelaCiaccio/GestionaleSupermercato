import { SidebarTrigger } from '../sidebar';
import React from 'react';
import { cn } from '@/lib/utils';
import { DarkModeToggle } from '../dark-mode-toggle';
import { Breadcrumb, Breadcrumbs } from '../breadcrumbs';

export function DashboardHeader({
  breadcrumbs,
  className,
  ...props
}: React.ComponentProps<'header'> & { breadcrumbs: Breadcrumb[] }) {
  return (
    <header
      className={cn(
        'flex h-16 shrink-0 items-center justify-between gap-2 border-b px-4',
        className
      )}
      {...props}
    >
      <div className="flex items-center gap-2.5">
        <SidebarTrigger className="-ml-1 cursor-pointer" />
        <Breadcrumbs breadcrumbs={breadcrumbs} />
      </div>

      <div className="flex items-center gap-2.5">
        <DarkModeToggle />
      </div>
    </header>
  );
}
