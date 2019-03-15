/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { Observable, of } from 'rxjs';

import { PortafolioTestModule } from '../../../test.module';
import { SocialAccountUpdateComponent } from 'app/entities/social-account/social-account-update.component';
import { SocialAccountService } from 'app/entities/social-account/social-account.service';
import { SocialAccount } from 'app/shared/model/social-account.model';

describe('Component Tests', () => {
    describe('SocialAccount Management Update Component', () => {
        let comp: SocialAccountUpdateComponent;
        let fixture: ComponentFixture<SocialAccountUpdateComponent>;
        let service: SocialAccountService;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [PortafolioTestModule],
                declarations: [SocialAccountUpdateComponent]
            })
                .overrideTemplate(SocialAccountUpdateComponent, '')
                .compileComponents();

            fixture = TestBed.createComponent(SocialAccountUpdateComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(SocialAccountService);
        });

        describe('save', () => {
            it('Should call update service on save for existing entity', fakeAsync(() => {
                // GIVEN
                const entity = new SocialAccount(123);
                spyOn(service, 'update').and.returnValue(of(new HttpResponse({ body: entity })));
                comp.socialAccount = entity;
                // WHEN
                comp.save();
                tick(); // simulate async

                // THEN
                expect(service.update).toHaveBeenCalledWith(entity);
                expect(comp.isSaving).toEqual(false);
            }));

            it('Should call create service on save for new entity', fakeAsync(() => {
                // GIVEN
                const entity = new SocialAccount();
                spyOn(service, 'create').and.returnValue(of(new HttpResponse({ body: entity })));
                comp.socialAccount = entity;
                // WHEN
                comp.save();
                tick(); // simulate async

                // THEN
                expect(service.create).toHaveBeenCalledWith(entity);
                expect(comp.isSaving).toEqual(false);
            }));
        });
    });
});
