import { Component, OnInit } from '@angular/core';
import { Observable } from 'rxjs';
import { WasteRequest } from 'src/app/webportal/pojo/waste-request';
import { CustomerWasteRequestService } from 'src/app/webportal/services/customer-waste-request.service';

@Component({
  selector: 'app-view-agent-confirmed-request',
  templateUrl: './view-agent-confirmed-request.component.html',
  styleUrls: ['./view-agent-confirmed-request.component.scss']
})
export class ViewAgentConfirmedRequestComponent implements OnInit {

  retrieveConfirmes : Observable<WasteRequest[]>
  requestEmptyListFlag = false;
  requests: WasteRequest;

  constructor(private customerWasteRequestService:CustomerWasteRequestService) { }

  ngOnInit() {
    this.reloadData();
  }

  reloadData(){

    this.customerWasteRequestService.getConfirmedWasteRequestList().subscribe(
      (data) => {
        this.retrieveConfirmes = data;
        if (data.length > 0) {
          this.requestEmptyListFlag = false;
          console.log('test' + this.retrieveConfirmes);
        } else {
          this.requestEmptyListFlag = true;
        }
      },
      (error) => {
        console.log(error);
      }
    );
  }

  confirm(id:number){
    var type;
    var sum;
      this.customerWasteRequestService.getCustomerWasteRequest(id).subscribe(
        (data) => {
          this.requests = data;
          this.requests.status = 'Completed';
         
          console.log(data);
          // type = this.requests.wasteType;
          // sum = this.requests.quantity;
          // this.getType(type,sum);
  
          this.customerWasteRequestService
          .updateCustomerWasteRequest(id, this.requests)
          .subscribe(
            (data) => console.log(data),
            (error) => {
              console.log(error);
            }
          );
        },
        (error) => console.log(error)
      );
  
      
     
     
      this.reloadData();
    
  }

}
