import { element, by, ElementFinder } from 'protractor';

export class UserEventComponentsPage {
    createButton = element(by.id('jh-create-entity'));
    deleteButtons = element.all(by.css('jhi-user-event div table .btn-danger'));
    title = element.all(by.css('jhi-user-event div h2#page-heading span')).first();

    async clickOnCreateButton() {
        await this.createButton.click();
    }

    async clickOnLastDeleteButton() {
        await this.deleteButtons.last().click();
    }

    async countDeleteButtons() {
        return this.deleteButtons.count();
    }

    async getTitle() {
        return this.title.getText();
    }
}

export class UserEventUpdatePage {
    pageTitle = element(by.id('jhi-user-event-heading'));
    saveButton = element(by.id('save-entity'));
    cancelButton = element(by.id('cancel-save'));
    userIdInput = element(by.id('field_userId'));
    eventIdInput = element(by.id('field_eventId'));
    avisInput = element(by.id('field_avis'));
    createdInput = element(by.id('field_created'));

    async getPageTitle() {
        return this.pageTitle.getText();
    }

    async setUserIdInput(userId) {
        await this.userIdInput.sendKeys(userId);
    }

    async getUserIdInput() {
        return this.userIdInput.getAttribute('value');
    }

    async setEventIdInput(eventId) {
        await this.eventIdInput.sendKeys(eventId);
    }

    async getEventIdInput() {
        return this.eventIdInput.getAttribute('value');
    }

    async setAvisInput(avis) {
        await this.avisInput.sendKeys(avis);
    }

    async getAvisInput() {
        return this.avisInput.getAttribute('value');
    }

    async setCreatedInput(created) {
        await this.createdInput.sendKeys(created);
    }

    async getCreatedInput() {
        return this.createdInput.getAttribute('value');
    }

    async save() {
        await this.saveButton.click();
    }

    async cancel() {
        await this.cancelButton.click();
    }

    getSaveButton(): ElementFinder {
        return this.saveButton;
    }
}

export class UserEventDeleteDialog {
    private dialogTitle = element(by.id('jhi-delete-userEvent-heading'));
    private confirmButton = element(by.id('jhi-confirm-delete-userEvent'));

    async getDialogTitle() {
        return this.dialogTitle.getText();
    }

    async clickOnConfirmButton() {
        await this.confirmButton.click();
    }
}
