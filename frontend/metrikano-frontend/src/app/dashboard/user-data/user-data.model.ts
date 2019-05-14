export class UserData {
  constructor(public recordID: string, public userID: number, public userType: string, public firstName: string, public lastName: string) {}
}

export class RecordDTO {
  constructor(public username: string, public recordID: string, public recordType: string) {}
}

export class UserClassificationData {
  constructor(public link?: string) {}
}
