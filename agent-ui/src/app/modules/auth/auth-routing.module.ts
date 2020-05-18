import { Routes, RouterModule } from '@angular/router';
import { NgModule } from '@angular/core';
import { AuthComponent } from './auth.component';
import { SignupRequestSentComponent } from './components/signup-request-sent/signup-request-sent.component';

const routes: Routes = [
    {
        path: '',
        component: AuthComponent,
        children: [
            { path: 'request-sent', component: SignupRequestSentComponent }    
        ]
    }
]

@NgModule({
    imports: [RouterModule.forChild(routes)],
    exports: [RouterModule]
})
export class AuthRoutingModule { }
