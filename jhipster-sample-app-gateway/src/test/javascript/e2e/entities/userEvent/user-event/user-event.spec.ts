/* tslint:disable no-unused-expression */
import { browser, ExpectedConditions as ec, promise } from 'protractor';
import { NavBarPage, SignInPage } from '../../../page-objects/jhi-page-objects';

import { UserEventComponentsPage, UserEventDeleteDialog, UserEventUpdatePage } from './user-event.page-object';

const expect = chai.expect;

describe('UserEvent e2e test', () => {
    let navBarPage: NavBarPage;
    let signInPage: SignInPage;
    let userEventUpdatePage: UserEventUpdatePage;
    let userEventComponentsPage: UserEventComponentsPage;
    let userEventDeleteDialog: UserEventDeleteDialog;

    before(async () => {
        await browser.get('/');
        navBarPage = new NavBarPage();
        signInPage = await navBarPage.getSignInPage();
        await signInPage.autoSignInUsing('admin', 'admin');
        await browser.wait(ec.visibilityOf(navBarPage.entityMenu), 5000);
    });

    it('should load UserEvents', async () => {
        await navBarPage.goToEntity('user-event');
        userEventComponentsPage = new UserEventComponentsPage();
        await browser.wait(ec.visibilityOf(userEventComponentsPage.title), 5000);
        expect(await userEventComponentsPage.getTitle()).to.eq('User Events');
    });

    it('should load create UserEvent page', async () => {
        await userEventComponentsPage.clickOnCreateButton();
        userEventUpdatePage = new UserEventUpdatePage();
        expect(await userEventUpdatePage.getPageTitle()).to.eq('Create or edit a User Event');
        await userEventUpdatePage.cancel();
    });

    it('should create and save UserEvents', async () => {
        const nbButtonsBeforeCreate = await userEventComponentsPage.countDeleteButtons();

        await userEventComponentsPage.clickOnCreateButton();
        await promise.all([
            userEventUpdatePage.setUserIdInput('userId'),
            userEventUpdatePage.setEventIdInput('eventId'),
            userEventUpdatePage.setAvisInput('avis'),
            userEventUpdatePage.setCreatedInput('2000-12-31')
        ]);
        expect(await userEventUpdatePage.getUserIdInput()).to.eq('userId');
        expect(await userEventUpdatePage.getEventIdInput()).to.eq('eventId');
        expect(await userEventUpdatePage.getAvisInput()).to.eq('avis');
        expect(await userEventUpdatePage.getCreatedInput()).to.eq('2000-12-31');
        await userEventUpdatePage.save();
        expect(await userEventUpdatePage.getSaveButton().isPresent()).to.be.false;

        expect(await userEventComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeCreate + 1);
    });

    it('should delete last UserEvent', async () => {
        const nbButtonsBeforeDelete = await userEventComponentsPage.countDeleteButtons();
        await userEventComponentsPage.clickOnLastDeleteButton();

        userEventDeleteDialog = new UserEventDeleteDialog();
        expect(await userEventDeleteDialog.getDialogTitle()).to.eq('Are you sure you want to delete this User Event?');
        await userEventDeleteDialog.clickOnConfirmButton();

        expect(await userEventComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeDelete - 1);
    });

    after(async () => {
        await navBarPage.autoSignOut();
    });
});
