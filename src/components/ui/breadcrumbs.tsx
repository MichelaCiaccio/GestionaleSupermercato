'use client';

import {
  Breadcrumb,
  BreadcrumbEllipsis,
  BreadcrumbItem,
  BreadcrumbLink,
  BreadcrumbList,
  BreadcrumbPage,
  BreadcrumbSeparator,
} from '@/components/ui/breadcrumb';
import { Button } from '@/components/ui/button';
import {
  Drawer,
  DrawerClose,
  DrawerContent,
  DrawerDescription,
  DrawerFooter,
  DrawerHeader,
  DrawerTitle,
  DrawerTrigger,
} from '@/components/ui/drawer';
import {
  DropdownMenu,
  DropdownMenuContent,
  DropdownMenuItem,
  DropdownMenuTrigger,
} from '@/components/ui/dropdown-menu';
import { useIsMobile } from '@/hooks/use-mobile';
import * as React from 'react';
import Link from 'next/link';

const ITEMS_TO_DISPLAY = 3;

export type Breadcrumb = {
  label: string;
  href?: string;
};

export function Breadcrumbs({ breadcrumbs }: { breadcrumbs: Breadcrumb[] }) {
  const isMobile = useIsMobile();

  if (breadcrumbs.length === 0) return;

  if (breadcrumbs.length === 1) {
    const item = breadcrumbs[0];

    return (
      <Breadcrumb>
        <BreadcrumbList>
          <BreadcrumbItem>
            <BreadcrumbPage className="max-w-20 truncate md:max-w-none">
              {item.label}
            </BreadcrumbPage>
          </BreadcrumbItem>
        </BreadcrumbList>
      </Breadcrumb>
    );
  }

  let numItemsToSlice = ITEMS_TO_DISPLAY - breadcrumbs.length;
  if (breadcrumbs.length > ITEMS_TO_DISPLAY) {
    numItemsToSlice = -ITEMS_TO_DISPLAY + 1;
  }

  return (
    <Breadcrumb>
      <BreadcrumbList>
        {!isMobile && (
          <>
            <BreadcrumbItem>
              <BreadcrumbLink href={breadcrumbs[0].href}>
                {breadcrumbs[0].label}
              </BreadcrumbLink>
            </BreadcrumbItem>
            <BreadcrumbSeparator />
          </>
        )}

        <InteractiveEllipses breadcrumbs={breadcrumbs} />

        {breadcrumbs.slice(numItemsToSlice).map((item, index) => (
          <BreadcrumbItem key={index}>
            {item.href ? (
              <>
                <BreadcrumbLink asChild>
                  <Link href={item.href}>{item.label}</Link>
                </BreadcrumbLink>
                <BreadcrumbSeparator />
              </>
            ) : (
              <BreadcrumbPage>{item.label}</BreadcrumbPage>
            )}
          </BreadcrumbItem>
        ))}
      </BreadcrumbList>
    </Breadcrumb>
  );
}

export function InteractiveEllipses({
  breadcrumbs,
}: {
  breadcrumbs: Breadcrumb[];
}) {
  const [open, setOpen] = React.useState(false);
  const isMobile = useIsMobile();

  if (breadcrumbs.length <= ITEMS_TO_DISPLAY && !isMobile) return null;

  if (isMobile) {
    return (
      <>
        <Drawer open={open} onOpenChange={setOpen}>
          <DrawerTrigger aria-label="Toggle Menu">
            <BreadcrumbEllipsis className="h-4 w-4" />
          </DrawerTrigger>
          <DrawerContent>
            <DrawerHeader className="text-left">
              <DrawerTitle>Navigate to</DrawerTitle>
              <DrawerDescription>
                Select a page to navigate to.
              </DrawerDescription>
            </DrawerHeader>
            <div className="grid gap-1 px-4">
              {breadcrumbs.slice(0, -1).map((item, index) => (
                <Link
                  key={index}
                  href={item.href ? item.href : '#'}
                  className="py-1 text-sm"
                >
                  {item.label}
                </Link>
              ))}
            </div>
            <DrawerFooter className="pt-4">
              <DrawerClose asChild>
                <Button variant="outline">Close</Button>
              </DrawerClose>
            </DrawerFooter>
          </DrawerContent>
        </Drawer>
        <BreadcrumbSeparator />
      </>
    );
  }

  return (
    <>
      <DropdownMenu open={open} onOpenChange={setOpen}>
        <DropdownMenuTrigger
          className="flex items-center gap-1"
          aria-label="Toggle menu"
        >
          <BreadcrumbEllipsis className="h-4 w-4" />
        </DropdownMenuTrigger>
        <DropdownMenuContent align="start">
          {breadcrumbs.slice(1, -2).map((item, index) => (
            <DropdownMenuItem key={index}>
              <Link href={item.href ? item.href : '#'}>{item.label}</Link>
            </DropdownMenuItem>
          ))}
        </DropdownMenuContent>
      </DropdownMenu>
      <BreadcrumbSeparator />
    </>
  );
}
