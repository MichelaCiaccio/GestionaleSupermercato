import {
  Sidebar,
  SidebarContent,
  SidebarFooter,
  SidebarGroup,
  SidebarGroupContent,
  SidebarGroupLabel,
  SidebarMenu,
  SidebarMenuButton,
  SidebarMenuItem,
} from '@/components/ui/sidebar';
import {
  BarChart3,
  Boxes,
  Factory,
  Home,
  Megaphone,
  Package,
  Percent,
  Receipt,
  Users,
} from 'lucide-react';
import { SidebarUser, SidebarUserProps } from './sidebar-user';
import Link from 'next/link';

export const items = [
  {
    title: 'Home',
    url: '/',
    icon: Home,
  },
  {
    title: 'Products',
    url: '/products',
    icon: Package,
  },
  {
    title: 'Stocks',
    url: '/stocks',
    icon: Boxes,
  },
  {
    title: 'Suppliers',
    url: '/suppliers',
    icon: Factory,
  },
  {
    title: 'Sales',
    url: '/sales',
    icon: Receipt,
  },
  {
    title: 'Discounts',
    url: '/discounts',
    icon: Percent,
  },
  {
    title: 'Promotions',
    url: '/promotions',
    icon: Megaphone,
  },
  {
    title: 'Users',
    url: '/users',
    icon: Users,
  },
  {
    title: 'Analytics',
    url: '/analytics',
    icon: BarChart3,
  },
];

export function AppSidebar({
  user,
  ...props
}: React.ComponentProps<'div'> & SidebarUserProps) {
  return (
    <Sidebar {...props}>
      <SidebarContent>
        <SidebarGroup>
          <SidebarGroupLabel>SupermarketOS</SidebarGroupLabel>
          <SidebarGroupContent>
            <SidebarMenu>
              {items.map((item) => (
                <SidebarMenuItem key={item.title}>
                  <SidebarMenuButton asChild>
                    <Link href={`/dashboard${item.url}`}>
                      <item.icon />
                      <span>{item.title}</span>
                    </Link>
                  </SidebarMenuButton>
                </SidebarMenuItem>
              ))}
            </SidebarMenu>
          </SidebarGroupContent>
        </SidebarGroup>
      </SidebarContent>
      <SidebarFooter>
        <SidebarUser user={user} />
      </SidebarFooter>
    </Sidebar>
  );
}
