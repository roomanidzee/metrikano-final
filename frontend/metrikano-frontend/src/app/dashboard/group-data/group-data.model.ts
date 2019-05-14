export class GroupData {
  constructor(public groupID: string, public postText: string, public postType: string, public creationTime: string) {}
}

export class RecordDTO {
  constructor(public username: string, public recordID: string, public recordType: string) {}
}

export class GroupClassificationData {
  constructor(public link?: string) {}
}
