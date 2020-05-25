import { Component, OnInit, Optional, Inject } from '@angular/core';
import { AuthComponent } from '../../auth.component';
import { MatDialogRef } from '@angular/material/dialog';

@Component({
  selector: 'app-change-password',
  templateUrl: './change-password.component.html',
  styleUrls: ['./change-password.component.css']
})
export class ChangePasswordComponent implements OnInit {

  constructor(
    public dialogRef: MatDialogRef<AuthComponent>,
    // @Optional() @Inject(MAT_DIALOG_DATA) public data: any
  ) { }

  ngOnInit(): void {
  }

}
