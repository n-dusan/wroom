import { Component, OnInit } from '@angular/core';
import { AuthService } from 'src/app/modules/auth/service/auth.service';
import { ToastrService } from 'ngx-toastr';

@Component({
  selector: 'app-home',
  templateUrl: './home.component.html',
  styleUrls: ['./home.component.css']
})
export class HomeComponent implements OnInit {

  constructor(private authService: AuthService,
    private toastr: ToastrService) { }

  ngOnInit(): void {
  }

  testClick() {
    this.authService.test().subscribe(
      () => {
        this.toastr.success('You are allowed to test this button','Congrats!')
      },
      error => {
        this.toastr.error(error.error,'Error')
      }
    );
  }

}
