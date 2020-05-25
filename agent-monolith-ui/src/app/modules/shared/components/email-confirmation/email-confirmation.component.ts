import { Component, OnInit } from '@angular/core';
import { AuthService } from 'src/app/modules/auth/service/auth.service';
import { ActivatedRoute } from '@angular/router';
import { ToastrService } from 'ngx-toastr';

@Component({
  selector: 'app-email-confirmation',
  templateUrl: './email-confirmation.component.html',
  styleUrls: ['./email-confirmation.component.css']
})
export class EmailConfirmationComponent implements OnInit {

  constructor(private authService: AuthService,
    private route: ActivatedRoute,
    private toastr: ToastrService) { }

  ngOnInit(): void {
    const token = this.route.snapshot.paramMap.get('token');
    console.log(token)
    this.authService.confirmEmail(token).subscribe(
      data => {
        this.toastr.success('Please wait until your request is processed.', 'E-Mail successfully confirmed')
      },
      error => {
        this.toastr.error('Please try again.', 'Error')
      }
    );
  }

}
