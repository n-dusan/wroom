import { Component, Input, OnInit } from '@angular/core';
import { FormControl } from '@angular/forms';
import { ValidationService } from '../validation.service';

@Component({
  selector: 'control-messages',
  template: `<div *ngIf="errorMessage !== null" color="warn">
    {{errorMessage}}
  </div>`
})
export class ControlMessagesComponent implements OnInit {

  @Input() control: FormControl;
  constructor() {}


  ngOnInit(): void {}

  get errorMessage(): string {
    for (let propertyName in this.control.errors) {
      if (
        this.control.errors.hasOwnProperty(propertyName) &&
        this.control.touched
      ) {
        return ValidationService.getValidatorErrorMessage(
          propertyName,
          this.control.errors[propertyName]
        );
      }
    }

    return null;
  }
}
