export interface Customer {
  username: string
  password: string
  name: string
  age?: number
  gender?: string
  location?: string
}

export interface UserResponse {
  id: number;
  username: string;
}

export interface MessageResponse {
  message: string;
}

export interface RegisterResponse {
  messageResponse: MessageResponse;
  userResponse: UserResponse;
}

export interface LoginRequest {
  username: string;
  password: string;
}

export interface LoginResponse {
  token: string;
  userResponse: UserResponse;
}

export interface Charity {
  id: number;
  title: string;
  image: string;
  description: string;
  donationCurrent?: number;
  donationRequired?: number;
  participantsCurrent?: number;
  participantsRequired?: number;
  donateAmount?: number;
  //creator: string
  //status
}

export interface CustomerInfo {
  token: string;
  id: number;
  name: string;
}

export interface Donate {
  id: number;
  amount: number;
}

export interface ProfileInfo {
  username: string;
  name: string;
  age?: number;
  gender?: string;
  location?: string;
  balance?: number;
}

export interface Transaction {
  charity: Charity;
  message: string;
}
