import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { environment } from 'src/environments/environment';
import { TokenService } from './token.service';

@Injectable({
  providedIn: 'root',
})
export class BackendService {
  servicelink = environment.rest_url;

  constructor(private http: HttpClient, private tokenService: TokenService) { }

  userlogin(username, password) {
    return this.http.post<any>(this.servicelink + '/authenticate/user', { username: username, password: password });
  }

  userlogout(user) {
    return this.http.post<any>(this.servicelink + '/user/logout/' + user, {});
  }

  getusers() {
    return this.http.get<any>(this.servicelink + '/user/list');
  }

  saveuser(user) {
    return this.http.post<any>(this.servicelink + '/user/save', user);
  }

  deleteuser(id) {
    return this.http.delete<any>(this.servicelink + '/user/delete/' + id);
  }

  getsettings() {
    return this.http.get<any>(this.servicelink + '/dashboard/settings');
  }

  generatereceipt(txid) {
    window.open(this.servicelink + '/file/receipt?txid=' + txid);
  }

  validateresetpassword(user) {
    return this.http.post<any>(this.servicelink + '/user/validate/reset', user);
  }

  resetpassword(user) {
    return this.http.post<any>(this.servicelink + '/user/reset/password', user);
  }

  changepassword(user) {
    return this.http.post<any>(this.servicelink + '/user/changepassword', user);
  }

  getauditpage(row) {
    return this.http.get<any>(this.servicelink + '/user/activity/page?row=' + row);
  }

  getactivity(row, page) {
    return this.http.get<any>(this.servicelink + '/user/activity?row=' + row + '&page=' + page);
  }

  saveimage(req) {
    return this.http.post<any>(this.servicelink + '/product/image/save', req);
  }

  savepatient(patient) {
    return this.http.post<any>(this.servicelink + '/patient/save', patient);
  }

  updatepatient(patient) {
    return this.http.put<any>(this.servicelink + '/patient/update', patient);
  }

  getpatients(row, page) {
    return this.http.get<any>(this.servicelink + '/patient/list?row=' + row + '&page=' + page);
  }

  getpatient(id, approw = null, apppage = null, txrow = null, txpage = null, prescrow = null, prescpage = null) {
    return this.http.get<any>(this.servicelink + '/patient/get?id=' + id
      + '&approw=' + approw
      + '&apppage=' + apppage
      + '&txrow=' + txrow
      + '&txpage=' + txpage
      + '&prescrow=' + prescrow
      + '&prescpage=' + prescpage
    );
  }

  getpatientbyname(id, firstname, lastname) {
    return this.http.get<any>(this.servicelink + '/patient/get/public?id=' + id
      + '&firstname=' + firstname
      + '&lastname=' + lastname
    );
  }

  saveappointment(appointment) {
    return this.http.post<any>(this.servicelink + '/appointment/save', appointment);
  }

  updateappointment(appointment) {
    return this.http.put<any>(this.servicelink + '/appointment/update', appointment);
  }

  getappointments(row, page) {
    return this.http.get<any>(this.servicelink + '/appointment/list?row=' + row + '&page=' + page);
  }

  getappointment(id) {
    return this.http.get<any>(this.servicelink + '/appointment/get?id=' + id);
  }

  saveinventory(inventory) {
    return this.http.post<any>(this.servicelink + '/inventory/save', inventory);
  }

  getinventorylist(row, page) {
    return this.http.get<any>(this.servicelink + '/inventory/list?row=' + row + '&page=' + page);
  }

  wildsearchinventory(row, page, name) {
    return this.http.get<any>(this.servicelink + '/inventory/search?row=' + row + '&page=' + page + '&name=' + name);
  }

  saveinvoice(invoice) {
    return this.http.post<any>(this.servicelink + '/transaction/invoice/save', invoice);
  }

  getinvoicelist(row, page) {
    return this.http.get<any>(this.servicelink + '/transaction/invoice/list?row=' + row + '&page=' + page);
  }

  getinvoice(id) {
    return this.http.get<any>(this.servicelink + '/transaction/invoice/get?id=' + id);
  }

  getinventory(id) {
    return this.http.get<any>(this.servicelink + '/inventory/get?id=' + id);
  }

  updateinventory(inventory) {
    return this.http.put<any>(this.servicelink + '/inventory/update', inventory);
  }

  savepayment(payment) {
    return this.http.post<any>(this.servicelink + '/transaction/save', payment);
  }

  getuser(username) {
    return this.http.get<any>(this.servicelink + '/user/get?username=' + username);
  }

  updateuser(user) {
    return this.http.put<any>(this.servicelink + '/user/update', user);
  }

  gettransactions(invoiceid) {
    return this.http.get<any>(this.servicelink + '/transaction/list?invoiceid=' + invoiceid);
  }

  saveprescription(presc) {
    return this.http.post<any>(this.servicelink + '/patient/prescription/save', presc);
  }


  getdashboard(date?: string) {
    if (date != undefined && date != null) {
      return this.http.get<any>(this.servicelink + '/dashboard/get?monthDate=' + date);
    } else {
      return this.http.get<any>(this.servicelink + '/dashboard/get');
    }
  }

  saveallpatient(patients) {
    return this.http.post<any>(this.servicelink + '/patient/upload', patients);
  }


  getdentaldetails() {
    return this.http.get<any>(this.servicelink + '/service/dental-details');
  }

  updatedentaldetails(details) {
    return this.http.put<any>(this.servicelink + '/service/dental/update', details);
  }

  getservicelist(row, page) {
    return this.http.get<any>(this.servicelink + '/service/list?row=' + row + '&page=' + page);
  }

  getallservicelist() {
    return this.http.get<any>(this.servicelink + '/service/list/all');
  }

  saveservice(service) {
    return this.http.post<any>(this.servicelink + '/service/save', service);
  }

  deleteservice(serviceid) {
    return this.http.delete<any>(this.servicelink + '/service/delete/' + serviceid);
  }

}
