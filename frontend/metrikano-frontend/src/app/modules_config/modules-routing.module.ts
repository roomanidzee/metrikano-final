import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { RouterModule, Routes } from '@angular/router';

const routes: Routes = [
  {path: '', loadChildren: '../navigation/navigation.module#NavigationModule'},
  {path: 'dashboard', loadChildren: '../dashboard/dashboard.module#DashboardModule'},
  {path: 'security', loadChildren: '../security/security.module#SecurityModule'}
];

@NgModule({
  imports: [
    CommonModule,
    RouterModule.forChild(routes)
  ],
  declarations: [],
  exports: [RouterModule]
})
export class ModulesRoutingModule { }
