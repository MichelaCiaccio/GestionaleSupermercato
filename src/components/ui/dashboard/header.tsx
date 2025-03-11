import { Separator } from '@radix-ui/react-separator';
import { SidebarTrigger } from '../sidebar';
import React from 'react';
import { cn } from '@/lib/utils';

export default function Header({
  title,
  className,
  ...props
}: React.ComponentProps<'header'>) {
  return (
    <header
      className={cn(
        'flex h-16 shrink-0 items-center gap-2 border-b px-4',
        className
      )}
      {...props}
    >
      <SidebarTrigger className="-ml-1 cursor-pointer" />
      <Separator orientation="vertical" className="mr-2 h-4" />
      <h1>{title}</h1>
    </header>
  );
}
