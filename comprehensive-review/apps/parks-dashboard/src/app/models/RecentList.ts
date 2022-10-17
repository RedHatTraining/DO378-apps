export class RecentList<T> {

    private items: T[] = [];

    constructor(private readonly limit = 10) {}

    public static createFrom<U>(previous: RecentList<U>): RecentList<U> {
        const newList: RecentList<U> = new RecentList();
        newList.setItems(previous.getItems());

        return newList;
    }

    public add(item: T): RecentList<T> {
        this.items.unshift(item);
        this.items = this.items.slice(0, this.limit);

        return this;
    }


    public getItems(): T[] {
        return this.items;
    }

    public setItems(items: T[]): void {
        this.items = items;
    }

}