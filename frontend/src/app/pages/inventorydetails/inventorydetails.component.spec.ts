import { ComponentFixture, TestBed } from '@angular/core/testing';

import { InventorydetailsComponent } from './inventorydetails.component';

describe('InventorydetailsComponent', () => {
  let component: InventorydetailsComponent;
  let fixture: ComponentFixture<InventorydetailsComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ InventorydetailsComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(InventorydetailsComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
