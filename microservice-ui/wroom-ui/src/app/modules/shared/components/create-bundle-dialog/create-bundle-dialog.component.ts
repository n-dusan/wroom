import { Component, OnInit, Inject } from '@angular/core';
import { MatDialogRef, MAT_DIALOG_DATA } from '@angular/material/dialog';
import { RentRequest } from '../../models/rent-request.model';
import { ToastrService } from 'ngx-toastr';
import { RentsService } from '../../service/rents.service';

@Component({
  selector: 'app-create-bundle-dialog',
  templateUrl: './create-bundle-dialog.component.html',
  styleUrls: ['./create-bundle-dialog.component.css']
})
export class CreateBundleDialogComponent implements OnInit {

  displayedColumns: string[] = ['vehicle', 'from', 'to', 'check'];
  checked: RentRequest[] = [];
  bundled: boolean = false;

  constructor(public dialogRef: MatDialogRef<CreateBundleDialogComponent>,
    @Inject(MAT_DIALOG_DATA) public data: RentRequest[],
    private toastr: ToastrService,
    private requestService: RentsService
  ) { }

  ngOnInit(): void {
    console.log(this.data)
  }

  check(request: RentRequest) {
    console.log('checked request', request)
    const req = this.data.find(obj => {return obj.ad.id === request.ad.id});
    req.checked = !req.checked;
    if(req.checked) {
      this.checked.push(request);

    } else {
      const idx = this.checked.indexOf(request);
      if(idx) {
        this.checked.splice(idx, 1)
      }
    }
    console.log(this.checked);
  }

  bundleClick() {
    this.bundled = !this.bundled;
    console.log('bundled', this.bundled)
    if(this.bundled) {
      this.toastr.info('Bundle', 'Requests will be bundled together');
    } else {
      this.toastr.info('Bundle', 'Requests wont be bundled together');
    }
  }

  sendClick() {
    var checkedNum = 0;
    var forSending: RentRequest[] = [];
    for(let r of this.data) {
      if(r.checked) {
        checkedNum += 1;
        forSending.push(r);
      }
    }
    if(checkedNum == 0) {
      this.toastr.info('Please select at least one item', 'No selected items')
      return;
    }

    if(checkedNum > 1) {
      // Bundle
      if(this.bundled) {
        this.requestService.sendBundle(forSending).subscribe(
          data=> {
            console.log(data);
            this.toastr.success('Your request is sent to owner. Please wait untill it is processed', 'Success')
            this.dialogRef.close({ data: forSending });
          },
          error => {
            console.log(error);
            if(error.status == 403){
              this.toastr.error('Insufficient rights, please contact admin', 'Error')
            }else{
              this.toastr.error('You have to pay your debts first', 'Error')
            }
          }
        );
      } else {
        for(let req of forSending) {
          console.log('request', req);
          this.requestService.send(req).subscribe(
            data=> {
              console.log(data);
              this.toastr.success('Your request is sent to owner. Please wait untill it is processed', 'Success')
            },
            error => {
              console.log(error);
              if(error.status == 403){
                this.toastr.error('Insufficient rights, please contact admin', 'Error')
              }else{
                this.toastr.error('You have to pay your debts first', 'Error')
              }
            }
          );
        }
        this.dialogRef.close({ data: forSending });
      }

    } else {
      // Obican
      console.log('sent request',forSending[0]);
      this.requestService.send(forSending[0]).subscribe(
        data=> {
          console.log('sent request',data);
          if(data == null) {
            this.toastr.error('You have to pay your debts first', 'Error')
            return;
          }
          this.toastr.success('Your request is sent to owner. Please wait untill it is processed', 'Success')
          this.dialogRef.close({ data: forSending });
        },
        error => {
          console.log(error);
          if(error.status == 403){
            this.toastr.error('Insufficient rights, please contact admin', 'Error')
          }else{
            this.toastr.error('You have to pay your debts first', 'Error')
          }
        }
      );
    }
  }

}
