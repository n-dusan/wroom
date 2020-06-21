import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { FormGroup, FormBuilder, FormControl, Validators } from '@angular/forms';
import { AuthService } from 'src/app/modules/auth/service/auth.service';
import { ToastrService } from 'ngx-toastr';
import { MatchingPassword } from 'src/app/modules/auth/validators/matching-password.validator';
import { ResetPassword } from '../../models/reset-password.model';

@Component({
  selector: 'app-reset-password',
  templateUrl: './reset-password.component.html',
  styleUrls: ['./reset-password.component.css']
})
export class ResetPasswordComponent implements OnInit {

  token: string = '';
  form: FormGroup;

  constructor(private route: ActivatedRoute,
    private formBuilder: FormBuilder,
    private authService: AuthService,
    private toastr: ToastrService,
    private router: Router) { }

  ngOnInit(): void {
    this.route.paramMap.subscribe(params => {
      this.token = params.get('token');

      this.form = this.formBuilder.group({
        'pass': new FormControl(null, [Validators.required]),
        'repeat': new FormControl(null, [Validators.required])
      },
        {
          validator: MatchingPassword('pass', 'repeat')
        });
    })
  }

  submitClick() {
    var token = new ResetPassword();
    token.password = this.form.value.pass;
    token.token = this.token;

    this.authService.changePassword(token).subscribe(
      data => {
        this.toastr.success('Password is changed', 'Success')
        this.router.navigate(['/auth'])
      },
      error => {
        for(let er of error.errors) {
          this.toastr.error(er, 'Error')
        }
      }
    );
  }

}
