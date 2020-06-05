import { Component, OnInit, ViewChild } from '@angular/core';
import { Subject } from 'rxjs';
import { MatPaginator } from '@angular/material/paginator';
import { MatTableDataSource } from '@angular/material/table';
import { User } from '../../model/user.model';
import { UserService } from '../../services/user.service';
import { takeUntil } from 'rxjs/operators';
import { ToastrService } from 'ngx-toastr';

@Component({
  selector: 'app-manage-users',
  templateUrl: './manage-users.component.html',
  styleUrls: ['./manage-users.component.css']
})
export class ManageUsersComponent implements OnInit {

  private ngUnsubscribe = new Subject();

  @ViewChild(MatPaginator, {static: true}) paginator: MatPaginator;

  isLoadingResults: boolean = true;
  displayedColumns: string[] = ['email', 'name', 'surname', 'user-type', 'account-locked', 'lock', 'delete'];

  dataSource: MatTableDataSource<User> = new MatTableDataSource;

  constructor(
    private userService: UserService,
    private toastr: ToastrService
  ) { }

  ngOnInit(): void {
    this.dataSource.data = [];
    this.refresh()
    this.dataSource.paginator = this.paginator;
  }

  refresh() {
    this.userService.findAllEnabled().pipe(takeUntil(this.ngUnsubscribe)).subscribe((result: User[]) => {
      console.log('my dear users', result)
      this.dataSource.data = result;
    })
  }


  onLockUserClick(data: User ) {
    console.log('lock data', data)
    this.userService.lockUser(data.id).subscribe( response => {
      this.refresh();
    })
  }

  onEnableUserClick(data: User) {
    console.log('enable data', data)
    this.userService.activateUser(data.id).subscribe( response => {
      this.refresh();
    })
  }

  onDeleteUserClick(data: User) {
    console.log('delete data', data)
    this.userService.deleteUser(data.id).subscribe(response => {
      this.refresh();
    }, error => this.toastr.error('Something went wrong', 'Oops'))
  }

}
