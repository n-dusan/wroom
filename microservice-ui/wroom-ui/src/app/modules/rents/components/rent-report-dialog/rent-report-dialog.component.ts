import { Component, OnInit, Inject } from '@angular/core';
import { ToastrService } from 'ngx-toastr';
import { MAT_DIALOG_DATA, MatDialogRef } from '@angular/material/dialog';
import { FormBuilder, FormGroup, FormControl, Validators } from '@angular/forms';
import { RentReportService } from '../../services/rent-report.service';
import { RentReport } from 'src/app/modules/shared/models/rent-report.model';

@Component({
  selector: 'app-rent-report-dialog',
  templateUrl: './rent-report-dialog.component.html',
  styleUrls: ['./rent-report-dialog.component.css']
})
export class RentReportDialogComponent implements OnInit {


  checked: boolean = false;
  form: FormGroup;

  constructor(private formBuilder: FormBuilder,
    public dialogRef: MatDialogRef<any>,
    @Inject(MAT_DIALOG_DATA) public data: any,
    private toastr: ToastrService,
    private rentReportService: RentReportService,
  ) { }

  ngOnInit(): void {

    console.log('data', this.data.request)

    this.form = this.formBuilder.group({
      'miles': new FormControl(null, [Validators.required]),
      'notes': new FormControl(null, [Validators.required])
    });
  }


  onFormSubmit() {

    if (this.form.valid) {
      console.log('valid')
      console.log('miles  ', this.form.get('miles').value)
      console.log('notes  ', this.form.get('notes').value)



      let report: RentReport = new RentReport(null,
        this.form.get('miles').value,
        this.form.get('notes').value,
        this.data.request.id,
        null);

      this.rentReportService.create(report).subscribe((report: RentReport) => {
        this.toastr.success('Done.', 'Report')
        this.dialogRef.close();
        this.rentReportService.debts(report).subscribe(
          data => {
            console.log(data);
          }
        );
      }, error => this.toastr.error('Went wrong with making a report', 'Report'))

    }
  }

}
