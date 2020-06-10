import { Component, OnInit, Inject } from '@angular/core';
import { MatDialogRef, MAT_DIALOG_DATA } from '@angular/material/dialog';
import { FormGroup, FormBuilder, FormControl, Validators } from '@angular/forms';
import { MessageService } from '../../services/message.service';
import { RentsService } from '../../services/rents.service';
import { ToastrService } from 'ngx-toastr';
import { RentRequest } from 'src/app/modules/shared/models/rent-request';
import { VehicleService } from 'src/app/modules/shared/service/vehicle.service';
import { Message } from 'src/app/modules/shared/models/message.model';

@Component({
  selector: 'app-contact-owner',
  templateUrl: './contact-owner.component.html',
  styleUrls: ['./contact-owner.component.css']
})
export class ContactOwnerComponent implements OnInit {

  request: RentRequest;
  form: FormGroup;
  ownerId: number;

  loaded: boolean = false;

  constructor(public dialogRef: MatDialogRef<ContactOwnerComponent>,
    @Inject(MAT_DIALOG_DATA) public data,
    private formBuilder: FormBuilder,
    private messageService: MessageService,
    private rentService: RentsService,
    private vehicleService: VehicleService,
    private toastr: ToastrService) { }

  ngOnInit(): void {

    this.request = this.data.request;

    this.form = this.formBuilder.group({
      'title' : new FormControl(null, [Validators.required]),
      'content' : new FormControl(null, [Validators.required])
    });

    console.log(this.request)
    this.vehicleService.get(this.request?.ad?.vehicleId).subscribe(
      data => {
        this.ownerId = data.ownerId;
        this.loaded = true;
      },
      error => {
        this.toastr.error('Unexpected error has ocurred', 'Error')
      }
    );

  }

  send() {
    const message = new Message(this.ownerId,this.request.id,
      this.form.value.title, this.form.value.content);

      this.messageService.send(message).subscribe(
        data => {
          this.toastr.success('Your message is sent to owner.', 'Success')
          this.dialogRef.close();
        },
        error => {
          this.toastr.error('Unexpected error has ocurred', 'Error')
        }
      );

  }

}
