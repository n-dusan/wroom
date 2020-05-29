import { Component, OnInit } from '@angular/core';
import { AuthService } from 'src/app/modules/auth/service/auth.service';
import { ToastrService } from 'ngx-toastr';
import { Router } from '@angular/router';

@Component({
  selector: 'app-home',
  templateUrl: './home.component.html',
  styleUrls: ['./home.component.css']
})
export class HomeComponent implements OnInit {

  constructor(private authService: AuthService,
    private toastr: ToastrService,
    private router: Router) { }

  ngOnInit(): void {
  }

  search() {
    this.router.navigate(['/search'])
  }

  testRoleClick() {
    this.authService.testRole().subscribe(
      () => {
        this.toastr.success('You are allowed to test this button','Congrats!')
      },
      error => {
        this.toastr.error('You are not allowed to test that button','Error')
      }
    );
  }

  testPermissionClick() {
    this.authService.testPermission().subscribe(
      () => {
        this.toastr.success('You are allowed to test this button','Congrats!')
      },
      error => {
        this.toastr.error('You are not allowed to test that button','Error')
      }
    );
  }

}
