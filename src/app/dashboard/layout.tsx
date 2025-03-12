import { AppSidebar } from '@/components/ui/dashboard/app-sidebar';
import { SidebarProvider } from '@/components/ui/sidebar';
import { ReactNode } from 'react';

export default function DashboardLayout({ children }: { children: ReactNode }) {
  return (
    <SidebarProvider>
      <AppSidebar
        user={{
          name: 'Sherwin Arellano',
          avatar: '/',
          avatarFallback: 'SA',
          email: 's.arellano@esis-italia.com',
        }}
      />

      <div className="flex flex-1 flex-col">{children}</div>
    </SidebarProvider>
  );
}
