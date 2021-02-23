import { Component, OnInit, ViewChild, AfterViewInit } from '@angular/core';
import { FormBuilder } from '@angular/forms';
import { Router } from '@angular/router';
import { MessageService, ConfirmationService } from 'primeng/api';
import { BackendService } from 'src/app/service/backend.service';
import { TokenService } from 'src/app/service/token.service';
import dayGridPlugin from '@fullcalendar/daygrid';
import timeGridPlugin from '@fullcalendar/timegrid';
import interactionPlugin from '@fullcalendar/interaction';
import { FullCalendar } from 'primeng/fullcalendar';


@Component({
  selector: 'app-dashboard',
  templateUrl: './dashboard.component.html',
  styleUrls: ['./dashboard.component.css'],
})
export class DashboardComponent implements OnInit {

  constructor(
    private fb: FormBuilder,
    private service: BackendService,
    private messageService: MessageService,
    private confirmationService: ConfirmationService,
    private router: Router,
    private tokenService: TokenService
  ) { }

  @ViewChild('calendar')
  fullCalendar: FullCalendar;

  ngOnInit(): void {
    this.dashboard = {};
    this.getTime();
    this.getdashboard();
    this.events = [];
    this.options = {
      plugins: [dayGridPlugin, timeGridPlugin, interactionPlugin],
      defaultDate: this.formatDate(new Date()),
      header: {
        left: 'prev,next',
        center: 'title',
        right: 'dayGridMonth,timeGridWeek,timeGridDay'
        //right: 'addAppointmentButton, dayGridMonth,timeGridWeek,timeGridDay'
      },
      eventClick: (e) => {
        this.router.navigate(['back-office/main/appointment/' + e.event.id]);
      }
      /*customButtons: {
        addAppointmentButton: {
          text: "New appointment",
          click: (r) => {
            console.log('asd');
          }
        }
      }*/
    }
  }



  getTime() {
    setInterval(f => {
      this.dashboardtime = new Date();
    }, 1000);
  }

  dashboard: any;
  events: any[];
  options: any;
  dashboardtime: any;

  getdashboard(datestr?: string) {
    this.dashboard = {};
    this.service.getdashboard(datestr).subscribe(res => {
      this.dashboard = res;
      this.events = this.dashboard.schedules;
      this.events.forEach(i => {
      });
      this.bindEvents();
    }, err => {
      this.tokenService.checkSession(err);
    });
  }

  formatDate(date) {
    let d = new Date(date),
      month = '' + (d.getMonth() + 1),
      day = '' + d.getDate(),
      year = d.getFullYear();

    if (month.length < 2)
      month = '0' + month;
    if (day.length < 2)
      day = '0' + day;

    return [year, month, day].join('-');
  }

  bindEvents() {
    let prevButton = this.fullCalendar.el.nativeElement.getElementsByClassName("fc-prev-button");
    let nextButton = this.fullCalendar.el.nativeElement.getElementsByClassName("fc-next-button");
    nextButton[0].addEventListener('click', () => {
      let d: Date = new Date(this.fullCalendar.calendar.state.currentDate);
      this.getdashboard(d.toLocaleDateString());
    });
    prevButton[0].addEventListener('click', () => {
      let d: Date = new Date(this.fullCalendar.calendar.state.currentDate);
      this.getdashboard(d.toLocaleDateString());
    });
  }
}
