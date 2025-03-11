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
  Factory,
  Home,
  Megaphone,
  Package,
  Percent,
  Receipt,
  Users,
} from 'lucide-react';
import { SidebarUser } from './sidebar-user';

const items = [
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

export function AppSidebar() {
  return (
    <Sidebar>
      <SidebarContent>
        <SidebarGroup>
          <SidebarGroupLabel>Application</SidebarGroupLabel>
          <SidebarGroupContent>
            <SidebarMenu>
              {items.map((item) => (
                <SidebarMenuItem key={item.title}>
                  <SidebarMenuButton asChild>
                    <a href={item.url}>
                      <item.icon />
                      <span>{item.title}</span>
                    </a>
                  </SidebarMenuButton>
                </SidebarMenuItem>
              ))}
            </SidebarMenu>
          </SidebarGroupContent>
        </SidebarGroup>
      </SidebarContent>
      <SidebarFooter>
        <SidebarUser
          user={{
            name: 'Sherwin',
            avatar: '/',
            avatarFallback: 'SA',
            email: 's.arellano@esis-italia.com',
          }}
        />
      </SidebarFooter>
    </Sidebar>
  );
}
