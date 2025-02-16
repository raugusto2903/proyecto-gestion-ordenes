import { ComponentFixture, TestBed } from '@angular/core/testing';

import { CrearproductoComponent } from './crearproducto.component';

describe('CrearproductoComponent', () => {
  let component: CrearproductoComponent;
  let fixture: ComponentFixture<CrearproductoComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [CrearproductoComponent]
    });
    fixture = TestBed.createComponent(CrearproductoComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
