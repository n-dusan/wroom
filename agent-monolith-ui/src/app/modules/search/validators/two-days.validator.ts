import { FormGroup } from '@angular/forms';

export function TwoDayValidator(controlName: string) {
    return (formGroup: FormGroup) => {
        // console.log(formGroup);
        if (formGroup.controls[controlName].errors?.twoDays) {
            return;
        }

        const twoDaysMilliseconds = 1000 * 60 * 60 * 24 * 2;
        const fromDate = new Date(formGroup.value.from);
        // console.log('from', fromDate);

        var twodaysfromthen = fromDate.getTime() - twoDaysMilliseconds;
        const twodaysfromthenDate = new Date(twodaysfromthen);
        // console.log('two days from then', twodaysfromthenDate);

        const now = new Date();
        if (((now.getDate() + 1) > twodaysfromthenDate.getDate())
            && (now.getMonth() === twodaysfromthenDate.getMonth())) {

            // console.log(now.getDate() + 2, twodaysfromthenDate.getDate())
            // console.log(now.getMonth(), twodaysfromthenDate.getMonth())

            formGroup.controls[controlName].setErrors({ twoDays: true });
            return;
        } else {
            formGroup.controls[controlName].setErrors(null);
        }



    };
}