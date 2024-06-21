import styled from 'styled-components';
import { ReactComponent as FlightIcon } from '../icons/flight.svg';

import { ReactComponent as TrainIcon } from '../icons/train.svg';
import { ReactComponent as NetflixIcon } from '../icons/tv.svg';
import { ReactComponent as GoogleIcon } from '../icons/search.svg';
import { Component } from 'react';

type EEIndicatorProps = {
  type: 'flight' | 'train' | 'netflix' | 'google';
  usage: number;
};

function ComparisionIndicator({ type, usage }: EEIndicatorProps) {
  const header = (type: string): { title: string; unit: string; icon: any } => {
    switch (type) {
      default:
        return { title: '비행 거리', unit: 'gCO2e/km', icon: <FlightIcon /> };
      case 'train':
        return { title: '기차', unit: 'kWh/km', icon: <TrainIcon /> };
      case 'netflix':
        return { title: '넷플릭스', unit: 'kWh', icon: <NetflixIcon /> };
      case 'google':
        return { title: '구글 검색', unit: 'kWh', icon: <GoogleIcon /> };
    }
  };

  return (
    <CIWrapper>
      <CIHeader>
        {header(type).title}
        <span style={{ color: '#979797' }}>{header(type).unit}</span>
      </CIHeader>
      <CIBody type={type}>
        {header(type).icon}
        {/* {usage.toFixed(2)} */}
        {usage.toFixed(7)}
      </CIBody>
    </CIWrapper>
  );
}

// style
const CIWrapper = styled.div`
  display: flex;
  flex-direction: column;
  align-items: flex-start;
  gap: 4px;
  font-size: 16px;
`;
const CIHeader = styled.div`
  display: flex;
  flex-direction: row;
  justify-content: space-between;
  width: 100%;
  margin: 0;
`;
const CIBody = styled.div<{ type: string }>`
  display: flex;
  flex-direction: row;
  justify-content: space-between;
  align-items: center;
  padding: 16px;
  font-size: 24px;
  border-radius: 16px;
  width: 100%;
  box-sizing: border-box;
  border: 1px solid #3f51b5;
  background-color: white;
`;

export default ComparisionIndicator;
