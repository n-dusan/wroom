import { Component, OnInit, Optional, Inject } from '@angular/core';
import { MAT_DIALOG_DATA } from '@angular/material/dialog';
import { FormControl, Validators, FormBuilder, FormGroup } from '@angular/forms';
import { User } from 'src/app/modules/shared/models/user.model';
import { UserService } from '../../services/user.service';
import { ToastrService } from 'ngx-toastr';

@Component({
  selector: 'app-permissions',
  templateUrl: './permissions.component.html',
  styleUrls: ['./permissions.component.css']
})
export class PermissionsComponent implements OnInit {
  checked = true;
  permissionsForm: FormGroup;
  crudVehicleRole: string;
  crudAdRole: string;
  physicallyReserveRole: string;
  rentRole: string;
  chatRole: string;
  commentRole: string;
  rateRole: string;
  roleList: string[] = [];

  constructor(@Optional() @Inject(MAT_DIALOG_DATA) public data: any,
    private formBuilder: FormBuilder,
    private userService: UserService,
    private toastr: ToastrService) { }

  ngOnInit(): void {
    this.permissionsForm = this.formBuilder.group({
      crudVehicle: [true],
      crudAd: [true],
      physicallyReserve: [true],
      rent: [true],
      chat: [true],
      commentAndRate: [true]
    });
    this.crudVehicleRole = 'ROLE_CRUD_VEHICLE';
    this.crudAdRole = 'ROLE_CRUD_AD';
    this.rentRole = 'ROLE_RENTING_USER';
    this.commentRole = 'ROLE_RATING_COMMENTING_USER';
    this.physicallyReserveRole = 'ROLE_PHYSICALLY_RESERVE';
    this.chatRole= 'ROLE_CHATTING_USER';
  }

  addPermissions(list: string[], id: number) {
    console.log('tu sam')
    this.userService.permissions(list, id)
      .subscribe(data => {
        this.toastr.success('You have successfully set permissions!', 'Success')
      },
        error => {
          this.toastr.error('Error!', 'Error')
        });
  }

  onSubmit() {
    this.roleList = [];
    if (this.permissionsForm.value.crudVehicle == true) {
      this.roleList.push(this.crudVehicleRole)
    }
    if (this.permissionsForm.value.crudAd == true) {
      this.roleList.push(this.crudAdRole);
    }
    if (this.permissionsForm.value.rent == true) {
      this.roleList.push(this.rentRole);
    }
    if (this.permissionsForm.value.commentAndRate == true) {
      this.roleList.push(this.commentRole)
    }
    if (this.permissionsForm.value.physicallyReserve == true) {
      this.roleList.push(this.physicallyReserveRole);
    }
    if (this.permissionsForm.value.chat == true) {
      this.roleList.push(this.chatRole);
    }

    this.addPermissions(this.roleList, this.data.id);
  }

  
}

