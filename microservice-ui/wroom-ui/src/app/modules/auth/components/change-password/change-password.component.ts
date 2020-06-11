import { Component, OnInit, Optional, Inject } from '@angular/core';
import { AuthComponent } from '../../auth.component';
import { MatDialogRef } from '@angular/material/dialog';
import { FormGroup, FormBuilder, FormControl, Validators } from '@angular/forms';
import { AuthService } from '../../service/auth.service';
import { ToastrService } from 'ngx-toastr';
import { Router } from '@angular/router';

@Component({
  selector: 'app-change-password',
  templateUrl: './change-password.component.html',
  styleUrls: ['./change-password.component.css']
})
export class ChangePasswordComponent implements OnInit {

  form: FormGroup;

  constructor(
    public dialogRef: MatDialogRef<AuthComponent>,
    // @Optional() @Inject(MAT_DIALOG_DATA) public data: any
    private formBuilder: FormBuilder,
    private authService: AuthService,
    private toastr: ToastrService,
    private router: Router
  ) { }

  ngOnInit(): void {

    this.form = this.formBuilder.group({
      'email': new FormControl(null, [Validators.required, Validators.email])
    });

  }

  confirmClick() {
    console.log(this.form.value.email);
    this.authService.forgotPassword(this.form.value.email).subscribe(
      data => {
        this.toastr.info('We have sent you further instructions.', 'Check yout E-Mail')

        setTimeout(() => {
          this.dialogRef.close();
          this.router.navigate(['/home']);
        }, 3000)
        
      },
      error => {
        this.toastr.error()
      }
    );
  }

}
