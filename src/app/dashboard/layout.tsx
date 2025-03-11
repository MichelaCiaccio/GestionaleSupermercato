import { AppSidebar } from '@/components/ui/dashboard/app-sidebar';
import { Header } from '@/components/ui/dashboard/header';
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

      <div className="flex-1">
        <Header title="Dashboard" />
        {children}
      </div>
    </SidebarProvider>
  );
}
